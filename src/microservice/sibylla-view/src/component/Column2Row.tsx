import * as React from "react";
import * as ReactRouter from "react-router-dom";

import { default as ArticleMeta } from "./ArticleMeta";

import { default as Article } from "../lib/Article";

interface IColumn2RowProps {
    article1: Article;
    article2: Article;
    onClick?: (id: number) => void;
}

export default class Column2RowComponent extends React.Component<IColumn2RowProps> {

    private static readonly ARTICLE_1 = 1;
    private static readonly ARTICLE_2 = 2;

    render() {
        const { article1, article2 } = this.props;
        const link1 = `./article?id=${article1.id}`;
        const link2 = `./article?id=${article2.id}`;
        const img1 = {
            backgroundImage: `url(${article1.images[0]? article1.images[0].src : "./img/x-box.svg"})`
        }

        const img2 = {
            backgroundImage: `url(${article2.images[0]? article2.images[0].src : "./img/x-box.svg"})`
        }

        return (<div className="my-4 column-2-article row justify-content-between">
            <div className="col-5 article-card">
                <div className="article-cover" style={img1}></div>
                <div className="article-overview mt-3">
                    <ReactRouter.Link 
                        className="article-title-anchor" to={link1}
                        onClick={ () => this.handleArticleClick(Column2RowComponent.ARTICLE_1)}>
                        <h2>{article1.title}</h2>
                    </ReactRouter.Link>
                    <p>{article1.content.slice(0, 200) + "..."}</p>
                    <ArticleMeta
                        sourceName={article1.sourceName}
                        writtenDate={article1.writtenDate}
                        url={article1.url}/>
                </div>
            </div>
            <div className="article-card col-5">
            <div className="article-cover" style={img2}></div>
                <div className="article-overview mt-3">
                    <ReactRouter.Link 
                        className="article-title-anchor" to={link2}
                        onClick={() => this.handleArticleClick(Column2RowComponent.ARTICLE_2)}>
                        <h2>{article2.title}</h2>
                    </ReactRouter.Link>
                    <p>{article2.content.slice(0, 200) + "..."}</p>
                    <ArticleMeta
                        sourceName={article2.sourceName}
                        writtenDate={article2.writtenDate}
                        url={article2.url}/>
                </div>
            </div>
        </div>);
    }

    private handleArticleClick = (num: number) => {
        if (this.props.onClick) {
            switch (num) {
            case Column2RowComponent.ARTICLE_1:
                this.props.onClick(this.props.article1.id);
                break;
            case Column2RowComponent.ARTICLE_2:
                this.props.onClick(this.props.article2.id);
                break;
            }
        }
    }
}