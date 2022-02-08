import React, { useState } from "react";
import { createStyles, makeStyles } from "@material-ui/core/styles";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import Typography from "@material-ui/core/Typography";
import { Link } from "react-router-dom";
import Box from "@material-ui/core/Box";

export default function NavigationBar() {
    const classes = useStyles();
    return (
        <AppBar position="static" className={classes.appBar}>
            <Box
                display={"flex"}
                flexDirection={"row-reverse"}
                justifyContent={"start"}
            >
                <Toolbar>
                    <Typography variant="h6" className={classes.title}>
                        <Link to="/" className={classes.link}>
                            Главная
                        </Link>
                    </Typography>
                    <Typography variant="h6" className={classes.title}>
                        <Link to="/request" className={classes.link}>
                            Новый запрос
                        </Link>
                    </Typography>
                    <Typography variant="h6" className={classes.title}>
                        <Link to="/requests" className={classes.link}>
                            Все запросы
                        </Link>
                    </Typography>
                </Toolbar>
            </Box>
        </AppBar>
    );
}

const useStyles = makeStyles((theme) =>
    createStyles({
        menuButton: {
            marginRight: theme.spacing(2),
        },
        title: {
            marginRight: "2rem",
        },
        link: {
            color: "#fff",
            textDecoration: "none",
        },
        appBar: {
            zIndex: theme.zIndex.drawer + 1,
        },
    })
);