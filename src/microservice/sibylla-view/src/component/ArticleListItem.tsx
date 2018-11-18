import * as React from "react";
import { Link } from "react-router-dom";

import { default as Article } from "../lib/Article";
import Routes from "../lib/Routes";

interface IArticleListItemComponentProps {
    article: Article;
    onClick: (id: number) => void;
}

export default class ArticleListItemComponent extends React.Component<IArticleListItemComponentProps> {

    render() {
        const { props } = this;
        const { writtenDate } = props.article;
        const now = new Date();
        const dspYear = now.getUTCFullYear() !== writtenDate.getUTCFullYear();
        const dspMonthDay = now.getUTCMonth() !== writtenDate.getUTCMonth() || 
            now.getUTCDate() !== writtenDate.getUTCDate();
        const writtenDateStr = `${dspYear? writtenDate.getUTCFullYear() + ". " : ""}${dspMonthDay? writtenDate.getMonth() + ". " + writtenDate.getUTCDate() + ". " : ""}${writtenDate.getUTCHours()}:${writtenDate.getUTCMinutes()}`;
        
        return (<div className="article-list-item">
            <Link to={Routes.ROUTE_ARTICLE_CONTENT + "?id=" + props.article.id} onClick={() => this.props.onClick(props.article.id)}><h2>{props.article.title}</h2></Link>
            <div className="article-meta"><img src="./img/joongang_logo_circle.png"/><h6>{props.article.sourceName}</h6><h6>| {writtenDateStr}</h6><a href={props.article.url} className="goto-source">원문 보기</a></div>
            <p>{props.article.content.slice(0, 250) + "..."}</p>
        </div>);
    }
}