import React from "react";
import Home from "./pages/Home";
import {Route, Routes} from "react-router";
import NewRequestPage from "./pages/NewRequestPage";
import RequestsPage from "./pages/RequestsPage";
import NativeRequestsPage from "./pages/NativeRequestsPage";

/*Routes is used to be Switch*/
const Router = () => {
    /* nesting routes*/
    return (
        <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/request" element={<NewRequestPage />} />
            <Route path="/requests" element={<NativeRequestsPage />} />
        </Routes>
    );
};
export default Router;