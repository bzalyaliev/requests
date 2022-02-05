import React, {Component, useState} from "react";
import Dropdown from 'react-dropdown';
import 'react-dropdown/style.css';
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";

class NewRequestPage extends Component {
    constructor(props) {
        super(props);
        this.state = {
            requests: [],
            newRequest: {
                deadline: new Date()
            }
        };

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleTypeChange = this.handleTypeChange.bind(this);
        this.handleDeadlineChange = this.handleDeadlineChange.bind(this);
    }

    handleChange(e) {
        const key = e.target.name.split('.')[1]
        this.setState(prevState => ({
                newRequest: {
                    ...prevState.newRequest,
                    [key]: e.target.value
                }
            })
        );
    }

    handleTypeChange(event) {
        this.setState(prevState => ({
                newRequest: {
                    ...prevState.newRequest,
                    type: event.value
                }
            })
        );
    }

    handleDeadlineChange(event) {
        this.setState(prevState => ({
                newRequest: {
                    ...prevState.newRequest,
                    deadline: event
                }
            })
        );
    }

    handleSubmit(event) {
        alert('Отправляем запрос на новую заявку');
        const requestBody = {
            ...this.state.newRequest,
            mass: parseFloat(this.state.newRequest.mass)
        };
        fetch('/api/request', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(requestBody)
        })
            .then(response => {
                if (response.status !== 201) {
                    throw {
                        error: {
                            status: response.status
                        }
                    }
                } else {
                    return response.json()
                }
            })
            .then(newRequest => {
                this.setState(prevState => ({
                    ...prevState,
                    response: newRequest
                }))
            })
            .catch(error => {
                console.log(error)
                this.setState(prevState => ({
                    ...prevState,
                    response: error
                }))
            })
        event.preventDefault();
    }

    render() {
        const MyDatePicker = () => {
            return (
                <DatePicker selected={this.state.newRequest.deadline} dateFormat="dd.MM.yyyy"
                            onChange={this.handleDeadlineChange} required/>
            );
        };

        return <div>
            <form onSubmit={this.handleSubmit}>
                <label>
                    Инициатор:
                    <br/>
                    <input name="newRequest.originator" type="text" onChange={this.handleChange}
                           value={this.state.newRequest.originator} required/>
                </label>
                <br/>
                <label>
                    Тип:
                    <Dropdown options={['FLAKES', 'POWDER']}
                              onChange={this.handleTypeChange}
                              value={this.state.newRequest.type}
                              placeholder="Выберите тип:" required/>
                </label>
                <br/>
                <label>
                    Срок завершения:
                    <MyDatePicker/>
                </label>
                <br/>
                <label>
                    Масса:
                    <br/>
                    <input name="newRequest.mass" type="number" step=".01" onChange={this.handleChange}
                           value={this.state.newRequest.mass} placeholder="10.12" required/>
                </label>
                <br/>
                <label>
                    Задача:
                    <br/>
                    <textarea name="newRequest.objective" cols="50" rows="5" onChange={this.handleChange}
                              value={this.state.newRequest.objective} required/>
                </label>
                <br/>
                <label>
                    Комментарии:
                    <br/>
                    <textarea name="newRequest.comments" cols="30" rows="2" onChange={this.handleChange}
                              value={this.state.newRequest.comments}/>
                </label>
                <br/>
                <input type="submit" value="Submit"/>
            </form>
            <ResponseHandler response={this.state.response}/>
        </div>
    }
}

const ResponseHandler = ({response}) => {
    if (response) {
        if (response.error) {
            return (
                <div role="alert">
                    <p>Получена ошибка:</p>
                    <pre>{response.error.status}</pre>
                </div>
            )
        } else {
            return <div role="success">
                Запрос успешно отправлен
            </div>
        }
    }
    return <div/>
}

export default NewRequestPage;