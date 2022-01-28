import React, {Component} from "react";

class NewRequestPage extends Component {
    constructor(props) {
        super(props);
        this.state = {requests: []};
    }

    render() {
        return <div>
            hello
        </div>
    }

    componentDidMount() {
        fetch('http://localhost:8080/api/requests')
            .then(response => response.json())
            .then(data => this.setState({requests: data}));
    }
}

export default NewRequestPage;