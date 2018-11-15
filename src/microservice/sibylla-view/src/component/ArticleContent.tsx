import * as React from "react";
import * as qs from "querystring";
import { AxiosResponse } from "axios";
import { Location } from "history";

import { default as Api } from "../lib/Api";
import Article from "../lib/Article";

interface IArticleContentPageComponentProps {
    location: Location;
}

interface IArticleContentPageComponentState {
    article: Article;
    signedIn: boolean;
}

const style = {
    content: {
        whiteSpace: "pre-wrap" as "pre-wrap",
    }
};

export default class ArticleContentPageComponent extends React.Component<IArticleContentPageComponentProps, IArticleContentPageComponentState> {

    constructor(props: IArticleContentPageComponentProps) {
        super(props);

        this.state = {
            article: new Article(0, "", "", "", new Date(), "", []),
            signedIn: false,
        };
        
    }

    componentDidMount() {
        const articleId = qs.parse(this.props.location.search.split("?")[1])["id"];

        // Get article list
        Api.retrieveArticle(Number(articleId))
        .then((res: AxiosResponse) => {
            this.setState({
                article: new Article(
                    res.data["id"],
                    res.data["title"],
                    res.data["content"],
                    res.data["sourceName"],
                    new Date(res.data["writtenDate"]),
                    res.data["url"],
                    res.data["images"]),
            });
        });

    }

    render() {
        const { article } = this.state;
        const { writtenDate } = article;
        const now = new Date();
        const dspYear = now.getUTCFullYear() !== writtenDate.getUTCFullYear();
        const dspMonthDay = now.getUTCMonth() !== writtenDate.getUTCMonth() || 
            now.getUTCDate() !== writtenDate.getUTCDate();
        const writtenDateStr = `${dspYear? writtenDate.getUTCFullYear() + ". " : ""}${dspMonthDay? writtenDate.getMonth() + ". " + writtenDate.getUTCDate() + ". " : ""}${writtenDate.getUTCHours()}:${writtenDate.getUTCMinutes()}`;
        
        return (<div id="content">
            <h2 className="my-3">{article.title}</h2>
            <div className="article-meta"><img src="./img/joongang_logo_circle.png"/><h6>{article.sourceName}</h6><h6>| {writtenDateStr}</h6><a href={article.url} className="goto-source">원문 보기</a></div>
            <p className="my-3" style={style.content}>{article.content}</p>
        </div>);
    }
}
