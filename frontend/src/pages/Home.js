import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import { Button, Container } from 'reactstrap';

class Home extends Component {
    render() {
        return (
            <div>
                <Container fluid>
                  {/*  <Button color="link"><Link to="/requests">Все запросы</Link></Button>*/}
                    <Button color="link"><Link to="/request">Новый запрос</Link></Button>
                </Container>
            </div>
        );
    }
}

export default Home;