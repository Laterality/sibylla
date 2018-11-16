import * as React from "react";
import { AxiosResponse } from "axios";
import { withRouter, RouteComponentProps, Switch, Route } from "react-router-dom";
import { withCookies, ReactCookieProps } from "react-cookie";

import { default as Api } from "../lib/Api";
import { default as Routes } from "../lib/Routes";

import { default as Article } from "../lib/Article";
import ArticleImage from "../lib/ArticleImage";

import { default as Header } from "../component/Header";
import { default as HomeContent } from "../component/HomeContent";
import { default as ArticleContent } from "../component/ArticleContent";
import { default as Articles } from "../component/Articles";

interface IHomePageComponentProps extends RouteComponentProps, ReactCookieProps{ }

interface IHomePageComponentState {
    articles: Array<Article>;
    searchResults: Array<Article>;
    signedIn: boolean;
}

class HomePageComponent extends React.Component<IHomePageComponentProps, IHomePageComponentState> {

    // Time for waiting to judge if input stopped
    private readonly SEARCH_PATIENCE = 700;
    
    private searchTimer: number | null = null;

    constructor(props: IHomePageComponentProps) {
        super(props);
        let signedIn = false;
        if (this.props.cookies) {
            if (this.props.cookies.get("auth")) { signedIn = true; }
        }

        this.state = {
            articles: [],
            searchResults: [],
            signedIn,
        };
    }

    render() {

        return (
            <div>
                <Header
                    signedIn={this.state.signedIn}
                    onSignedClick={this.handleSignInClick}
                    onLogoutClick={this.handleLogout}
                    onSearchInputChange={this.handleSearchInputChange}/>
                
                <Switch>
                    <Route 
                        path={Routes.ROUTE_HOME} exact
                        render={() => 
                            <HomeContent
                                articles={this.state.articles}
                                onArticleClick={this.handleArticleClick}/>}
                    />
                    <Route 
                        path={Routes.ROUTE_ARTICLE_CONTENT}
                        render={() => 
                            <ArticleContent 
                                location={this.props.location}/>}
                    />
                    <Route 
                        path={Routes.ROUTE_ARTICLES}
                        render={() => 
                            <Articles
                                location={this.props.location}
                                onArticleClick={this.handleArticleClick}
                                articles={this.state.searchResults}/>}
                    />
                </Switch>
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

    private handleSignInClick = (email: string, password: string) => {
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

    private handleSearchInputChange = (value: string) => {
        value = value.trim()
        if (value.length === 0) {
            this.props.history.push(Routes.ROUTE_HOME);
            if (this.searchTimer != null) {
                clearTimeout(this.searchTimer);
                this.searchTimer = null;
            }
            return;
        }
        if (this.searchTimer !== null) {
            clearTimeout(this.searchTimer);
            this.searchTimer = null;
        }
        this.searchTimer = window.setTimeout(() => {
            Api.searchArticle(value)
            .then((res: AxiosResponse) => {
                this.props.history.push(Routes.ROUTE_ARTICLES);
                const aHits: Array<any> = res.data["data"]["hits"];
                const articles = aHits.map((obj: any) => {
                    // TODO: get sourceName and article images
                    const source = obj["_source"];
                    return new Article(
                        source["id"],
                        source["title"],
                        source["content"],
                        "",
                        new Date(source["writtenDate"]),
                        source["url"],
                        []);
                });
                this.setState({searchResults: articles});
            });
        }, this.SEARCH_PATIENCE);
    }
}

export default withRouter(withCookies(HomePageComponent) as any as React.ComponentType<IHomePageComponentProps>);
