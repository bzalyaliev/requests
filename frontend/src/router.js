import React from "react";
import Home from "./pages/Home";
import {Route, Routes} from "react-router";
import NewRequestPage from "./pages/NewRequestPage";

/*Routes is used to be Switch*/
const Router = () => {
    /* nesting routes*/
    return (
        <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/request" element={<NewRequestPage />} />
        </Routes>
    );

    /* object-based routes
    return useRoutes([
      { path: "/", element: <LandingPage /> },
      { path: "games", element: <Games /> },
      { path: "game-details/:id", element: <GameDetails /> },
      {
        path: "dashboard",
        element: <Dashboard />,
        children: [
          { path: "/", element: <DashboardDefaultContent /> },
          { path: "inbox", element: <Inbox /> },
          { path: "settings-and-privacy", element: <SettingsAndPrivacy /> },
          { path: "*", element: <NotFound /> },
        ],
      },
      { path: "*", element: <NotFound /> },
    ]);
  */
};
export default Router;