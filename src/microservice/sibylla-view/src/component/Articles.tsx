import * as React from "react";
import { Location } from "history";

import { default as Article } from "../lib/Article";


interface IArticlesComponentProps {
    location: Location;
    onArticleClick: (id: number) => void;
}

interface IArticlesComponentState {
    signedIn: boolean;
    articles: Array<Article>
}

export default class ArticlesComponent extends React.Component<IArticlesComponentProps, IArticlesComponentState> {

    constructor(p: IArticlesComponentProps) {
        super(p);

        this.state = {
            signedIn: false,
            articles: [],
        };
    }

    public render() {
        return (<div>

        </div>);
    }

}
