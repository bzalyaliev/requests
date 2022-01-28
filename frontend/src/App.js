import './App.css';
import {BrowserRouter} from "react-router-dom";
import NavigationBar from "./components/navigation-bar";
import Router from "./router";
import {Container} from "@material-ui/core";

function App() {
    return (
        <BrowserRouter>
            <NavigationBar />
            <Container>
                <Router />
            </Container>
        </BrowserRouter>
    );
}

export default App;
/*class App extends Component {
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
                        <Routes>
                            <Route path={'/request'} exact={true} component={NewRequestPage}/>
                            <Route render={() => <Navigate to="/" />} />
                        </Routes>
                    {/!*  <Route path={'/request/:id'} component={RequestPage}/>
                        <Route path={'/request'} exact={true} component={NewRequestPage}/>
                        <Route path={'/requests'} exact={true} component={RequestsPage}/>*!/}
                </div>
            </div>
        )
    };
}

export default App;*/
