import * as React from "react";

import { default as Article } from "../lib/Article";

import { default as ArticleListItem } from "./ArticleListItem";

interface IArticleListComponentProps {
    articles: Array<Article>;
}

export default class ArticleListComponent extends React.Component<IArticleListComponentProps> {

    render() {
        const { props } = this;
        const items = props.articles.map((v: Article) => {
            return <ArticleListItem article={v} key={v.id}/>;
        });
        
        return (<div className="article-list">
            {items}
        </div>);
    }
}