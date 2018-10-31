import * as React from "react";
import * as ReactRouter from "react-router-dom";

import { default as ArticleMeta } from "./ArticleMeta";

import { default as Article } from "../lib/Article";

interface IColumn1RowComponentProps {
    article: Article;
    onClick?: (id: number) => void;
}

export default class Column1RowComponent extends React.Component<IColumn1RowComponentProps> {

    render() {
        const { article } = this.props;
        const link = `./article?id=${article.id}`;

        const img = {
            backgroundImage: "url(" + article.images[0].src + ")"
        }
        return(<div className="column-1-article article-card row">
            <div style={img} className="article-cover col-7"></div>
            <div className="article-overview col-5 my-3">
                <ReactRouter.Link className="article-title-anchor" to={link} onClick={this.handleArticleClick}>
                    <h2>{article.title}</h2></ReactRouter.Link>
                <p>{article.content.slice(0, 300) + "..."}</p>
                <ArticleMeta
                    sourceName={article.sourceName}
                    writtenDate={article.writtenDate}
                    url={article.url}/>
            </div>

        </div>);
    }

    private handleArticleClick = () => {
        if (this.props.onClick) {
            this.props.onClick(this.props.article.id);
        }
    }
}