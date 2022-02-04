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
                deadLine: new Date()
            }
        };

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleTypeChange = this.handleTypeChange.bind(this);
        this.handleStatusChange = this.handleStatusChange.bind(this);
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

    handleStatusChange(event) {
        this.setState(prevState => ({
                newRequest: {
                    ...prevState.newRequest,
                    status: event.value
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
            .then(response => response.json())
            .catch(e => console.log(e))
        event.preventDefault();
    }

    render() {
        const MyDatePicker = () => {
            const [startDate] = useState(new Date());
            return (
                <DatePicker selected={startDate} onChange={this.handleDeadlineChange}/>
            );
        };

        return <div>
            <form onSubmit={this.handleSubmit}>
                <label>
                    Инициатор:
                    <input name="newRequest.originator" type="text" onChange={this.handleChange}
                           value={this.state.newRequest.originator}/>
                </label>
                <br/>
                <label>
                    Тип:
                    <Dropdown options={['FLAKES', 'POWDER']}
                              onChange={this.handleTypeChange}
                              value={this.state.newRequest.type}
                              placeholder="Выберите тип:"/>
                </label>
                <br/>
                <label>
                    Статус:
                    <Dropdown options={['GENERATED', 'IN_WORK', 'DONE', 'CANCELLED', 'REJECTED']}
                              onChange={this.handleStatusChange}
                              value={this.state.newRequest.status}
                              placeholder="Выберите статус:"/>
                </label>
                <br/>
                <MyDatePicker/>
                <br/>
                <label>
                    Масса:
                    <input name="newRequest.mass" type="number" step=".01" onChange={this.handleChange}
                           value={this.state.newRequest.mass}/>
                </label>
                <br/>
                <label>
                    Задача:
                    <input name="newRequest.objective" type="text" onChange={this.handleChange}
                           value={this.state.newRequest.objective}/>
                </label>
                <br/>
                <label>
                    Комментарии:
                    <input name="newRequest.comments" type="text" onChange={this.handleChange}
                           value={this.state.newRequest.comments}/>
                </label>
                <br/>
                <input type="submit" value="Submit"/>
            </form>
        </div>
    }
}

export default NewRequestPage;