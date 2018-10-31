import * as React from "react";
import { AxiosResponse } from "axios";
import { withRouter, RouteComponentProps } from "react-router-dom";

import { default as Api } from "../lib/Api";

import { default as Article } from "../lib/Article";

import { default as Header } from "../component/Header";
import { default as Content } from "../component/Content";
import { withCookies, ReactCookieProps } from "react-cookie";

interface IHomePageComponentProps extends RouteComponentProps, ReactCookieProps{ }

interface IHomePageComponentState {
    articles: Array<Article>;
    signedIn: boolean;
}

class HomePageComponent extends React.Component<IHomePageComponentProps, IHomePageComponentState> {

    constructor(props: IHomePageComponentProps) {
        super(props);
        let signedIn = false;
        if (this.props.cookies) {
            if (this.props.cookies.get("auth")) { signedIn = true; }
        }

        this.state = {
            articles: [],
            signedIn,
        };
    }

    render() {

        return (
            <div>
                <Header
                    signedIn={this.state.signedIn}
                    onSignedClick={this.handleSignInClick}
                    onLogoutClick={this.handleLogout}/>
                <Content
                    articles={this.state.articles}
                    onArticleClick={this.handleArticleClick}/>
            </div>
        );
    }

    componentDidMount() {
        // Get article list
        Api.retrieveArticles(21)
        .then((res: AxiosResponse) => {
            const articles = [];

            for (let a of res.data["articles"]) {
                articles.push(new Article(
                    a["id"], 
                    a["title"],
                    a["content"],
                    a["sourceName"],
                    new Date(a["writtenDate"]),
                    a["url"],
                    a["images"]));
            }
            this.setState({articles});
        });
    }

    private handleSignInClick = (email: string, password: string,) => {
        Api.signIn(email, password)
        .then((res) => {
            if (res.status === 200) {
                if (!this.props.cookies) { return; }
                this.props.cookies.set("auth", res.data["data"]["token"]);
                this.setState({signedIn: true});
            }
        });
    }

    private handleLogout = () => {
        if (!this.props.cookies) { return; }
        Api.logout(this.props.cookies.get("auth"))
        .then((res: AxiosResponse) => {
            if (res.status === 200) {
                this.setState({signedIn: false});
            }
        });
    }

    private handleArticleClick = (id: number) => {
        if (!this.state.signedIn) { return; }
        if (!this.props.cookies) { return; }
        Api.read(this.props.cookies.get("auth"), id)
        .then((res: AxiosResponse) => {
            // nothing to do
        });
    }
}

export default withRouter(withCookies(HomePageComponent));
