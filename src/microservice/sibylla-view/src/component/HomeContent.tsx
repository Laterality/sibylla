import * as React from "react";

import { default as Column1Row } from "./Column1Row";
import { default as Column2Row } from "./Column2Row";

import { default as Article } from "../lib/Article";


interface IHomeContentComponentProps {
    articles: Array<Article>;
    onArticleClick: (id: number) => void;
}

export default class HomeContentComponent extends React.Component<IHomeContentComponentProps> {

    constructor(props: any) {
        super(props);
    }

    render() {
        const { articles } = this.props;
        const lengthToShow = articles.length - articles.length % 3;
        const articleComponents = [];
        let t = 0;
        for (let i = 0; i < lengthToShow; i++) {
            const a = articles[i];
            switch (t % 2) {
            case 0:
                articleComponents.push(<Column1Row article={a} key={a.id}
                    onClick={this.handleArticleClick}/>);
                break;
            case 1:
                articleComponents.push(<Column2Row article1={articles[i]}
                    article2={articles[++i]} key={a.id}
                    onClick={this.handleArticleClick}/>)
                break;
            }
            t++;
        }
        
        return (<div id="content">
            {articleComponents}
        </div>);
    }

    private handleArticleClick = (id: number) => {
        this.props.onArticleClick(id);
    }
}
