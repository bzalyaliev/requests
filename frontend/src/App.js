import logo from './logo.svg';
import './App.css';
import {Link, Route, Routes} from "react-router-dom";
import Home from "./Home";
import {Component} from "react";

class App extends Component {
  render() {
    return (
        <div className="App">
            <div className="menu">
                <ul>
                    <li> <Link to="/request">Новый запрос</Link></li>
                    <li> <Link to="/requests">Все Запросы</Link></li>
                </ul>
            </div>
            <div className="App-intro">
                <Routes>
                  {/*  <Route path={'/request/:id'} component={RequestPage}/>
                    <Route path={'/request'} exact={true} component={NewRequestPage}/>
                    <Route path={'/requests'} exact={true} component={RequestsPage}/>*/}
                </Routes>
            </div>
        </div>
    )
  };
}

export default App;
