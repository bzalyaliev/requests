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
            newRequest: {}
        };

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleTypeChange = this.handleTypeChange.bind(this);
        this.handleStatusChange = this.handleStatusChange.bind(this);
        this.handleDeadlineChange = this.handleDeadlineChange.bind(this);
    }

    handleChange(e) {
        this.setState({
            [e.target.name]: e.target.value
        });
    }

    handleTypeChange(event) {
        this.setState({
            newRequest: {
                type: event.value
            }
        });
    }

    handleStatusChange(event) {
        this.setState({
            newRequest: {
                status: event.value
            }
        });
    }

    handleDeadlineChange(event) {
        this.setState({
            newRequest: {
                deadline: event
            }
        });
    }

    handleSubmit(event) {
        alert('Отправляем запрос на новую заявку');
        fetch('/api/request', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                    originator: this.state.newRequest.originator,
                    type: this.state.newRequest.type,
                    deadline: this.state.newRequest.deadline,
                    objective: this.state.newRequest.objective,
                    comments: this.state.newRequest.comments
                }
            )
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
                    <input name="newRequest.originator" type="text" onChange={this.handleChange} value={this.state.newRequest.originator}/>
                </label>
                <Dropdown options={['FLAKES', 'POWDER']} onChange={this.handleTypeChange}
                          value={this.state.newRequest.type}
                          placeholder="Выбери тип:"/>;
                <Dropdown options={['GENERATED', 'IN_WORK', 'DONE', 'CANCELLED', 'REJECTED']}
                          onChange={this.handleStatusChange}
                          value={this.state.newRequest.status} placeholder="Выбери тип:"/>;
                <MyDatePicker/>
                <label>
                    Масса:
                    <input name="newRequest.mass" type="number" onChange={this.handleChange} value={this.state.newRequest.mass}/>
                </label>
                <label>
                    Задача:
                    <input name="newRequest.objective" type="text" onChange={this.handleChange} value={this.state.newRequest.objective}/>
                </label>
                <label>
                    Комментарии:
                    <input name="newRequest.comments" type="text" onChange={this.handleChange} value={this.state.newRequest.comments}/>
                </label>
                <input type="submit" value="Submit"/>
            </form>
        </div>
    }
}

export default NewRequestPage;