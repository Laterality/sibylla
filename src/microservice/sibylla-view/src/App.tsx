import { MuiThemeProvider, createMuiTheme } from "@material-ui/core/styles";
import { CookiesProvider } from "react-cookie";
import * as React from "react";
import * as ReactRouter from "react-router-dom";

import { default as Home } from "./page/Home";
import { default as ArticleContent } from "./component/ArticleContent";

export default class App extends React.Component {

    render() {
        const theme = createMuiTheme({
            palette: {
                primary: {
                    main: "#3e2723",
                    light: "#6a4f4b",
                    dark: "1b0000",
                },
                secondary: {
                    main: "#fafafa",
                    light: "#ffffff",
                    dark: "#c7c7c7",
                },
            },
            typography: {
                useNextVariants: true,
            }
        });

        return (
        <CookiesProvider>
            <MuiThemeProvider theme={theme}>
                <ReactRouter.BrowserRouter>
                    <ReactRouter.Switch>
                        <ReactRouter.Route exact path="/sb" component={Home}/>
                        <ReactRouter.Route path="/sb/article" component={ArticleContent} />
                    </ReactRouter.Switch>
                </ReactRouter.BrowserRouter>
            </MuiThemeProvider>
        </CookiesProvider>);
    }
}