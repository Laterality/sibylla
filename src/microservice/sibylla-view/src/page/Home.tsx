import * as React from "react";
import { AxiosResponse } from "axios";
import { withRouter, RouteComponentProps } from "react-router-dom";

import { default as Api } from "../lib/Api";

import { default as Article } from "../lib/Article";
import ArticleImage from "../lib/ArticleImage";

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
        if (this.state.signedIn && this.props.cookies) {
            Api.retrieveRecommends(this.props.cookies.get("auth"))
            .then((res: AxiosResponse) => {
                const ids = res.data["data"].map((obj: any) => obj["articleId"]);
                
                Api.retrieveArticlesWithIds(ids)
                .then((res: AxiosResponse) => {
                    const articles = res.data["data"]
                    .map((obj: any) => new Article(
                        obj["id"],
                        obj["title"],
                        obj["content"],
                        obj["sourceName"],
                        new Date(obj["writtenDate"]),
                        obj["url"],
                        obj["images"].map((img: any) => 
                            new ArticleImage(
                                img["id"],
                                img["articleId"],
                                img["src"],
                                img["regDate"],
                                img["modDate"]))));

                    this.setState({articles});
                });
            });
        } else {
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
                        a["images"].map((img: any) => 
                            new ArticleImage(
                                img["id"],
                                img["articleId"],
                                img["src"],
                                img["regDate"],
                                img["modDate"]))));
                }
                this.setState({articles});
            });
        }
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

export default withRouter(withCookies(HomePageComponent) as any as React.ComponentType<IHomePageComponentProps>);
