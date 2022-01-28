import './App.css';
import {Link} from "react-router-dom";
import {Component} from "react";

class App extends Component {
    render() {
        return (
            <div className="App">
                <div className="menu">
                    <ul>
                        <li><Link to="/request">Новый запрос</Link></li>
                        <li><Link to="/requests">Все Запросы</Link></li>
                    </ul>
                </div>
                <div className="App-intro">
                    {/*  <Route path={'/request/:id'} component={RequestPage}/>
                        <Route path={'/request'} exact={true} component={NewRequestPage}/>
                        <Route path={'/requests'} exact={true} component={RequestsPage}/>*/}
                </div>
            </div>
        )
    };
}

export default App;
