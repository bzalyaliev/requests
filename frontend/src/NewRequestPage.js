import {Component} from "react";

class NewRequestPage extends Component {
    constructor(props) {
        super(props);
        this.state = {requests: []};
        this.remove = this.remove.bind(this);
    }

    componentDidMount() {
        fetch('/requests')
            .then(response => response.json())
            .then(data => this.setState({clients: data}));
    }
}