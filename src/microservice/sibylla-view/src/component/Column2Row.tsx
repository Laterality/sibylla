import * as React from "react";

import { default as ArticleMeta } from "./ArticleMeta";

import { default as Article } from "../lib/Article";

interface IColumn2RowProps {
    article1: Article;
    article2: Article;
}

export default class Column2RowComponent extends React.Component<IColumn2RowProps> {

    render() {
        const { article1, article2 } = this.props;
        const link1 = `./article?id=${article1.id}`;
        const link2 = `./article?id=${article2.id}`;
        const img1 = {
            backgroundImage: `url(${article1.images[0].src})`
        }

        const img2 = {
            backgroundImage: `url(${article2.images[0].src})`
        }

        return (<div className="my-4 column-2-article row justify-content-between">
            <div className="col-5 article-card">
                <div className="article-cover" style={img1}></div>
                <div className="article-overview mt-3">
                    <a className="article-title-anchor" href={link1}>
                        <h2>{article1.title}</h2>
                    </a>
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
                    <a className="article-title-anchor" href={link2}>
                        <h2>{article2.title}</h2>
                    </a>
                    <p>{article2.content.slice(0, 200) + "..."}</p>
                    <ArticleMeta
                        sourceName={article2.sourceName}
                        writtenDate={article2.writtenDate}
                        url={article2.url}/>
                </div>
            </div>
        </div>);
    }
}