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
