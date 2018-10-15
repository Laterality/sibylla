import * as React from "react";
import * as ReactRouter from "react-router-dom";
import * as qs from "querystring";

import Article from "../lib/Article";

interface IArticleContentPageComponentProps {
    location: Location;
    article: Article;
}

export default class ArticleContentPageComponent extends React.Component<IArticleContentPageComponentProps> {

    componentDidMount() {
        const articleId = qs.parse(this.props.location.search.split("?")[1])["id"]
        console.log(articleId);

    }

    render() {
        const { props } = this;
        const { writtenDate } = props.article;
        const now = new Date();
        const dspYear = now.getUTCFullYear() !== writtenDate.getUTCFullYear();
        const dspMonthDay = now.getUTCMonth() !== writtenDate.getUTCMonth() || 
            now.getUTCDate() !== writtenDate.getUTCDate();
        const writtenDateStr = `${dspYear? writtenDate.getUTCFullYear() + ". " : ""}${dspMonthDay? writtenDate.getMonth() + ". " + writtenDate.getUTCDate() + ". " : ""}${writtenDate.getUTCHours()}:${writtenDate.getUTCMinutes()}`;
        
        return (<div className="article-list-item">
            <h2>{props.article.title}</h2>
            <div className="article-meta"><img src="./img/joongang_logo_circle.png"/><h6>{props.article.sourceName}</h6><h6>| {writtenDateStr}</h6><a href={props.article.url} className="goto-source">원문 보기</a></div>
            <p>{props.article.content}</p>
        </div>);
    }
}