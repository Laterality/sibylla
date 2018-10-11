import * as React from "react";

import { default as Article } from "../lib/Article";

import ArticleListComponent, { default as ArticleList } from "./ArticleList";

interface IContentComponentState {
    articles: Array<Article>;
}

export default class ContentComponent extends React.Component<any, IContentComponentState> {

    constructor(props: any) {
        super(props);

        this.state = {
            articles: [
                new Article(1, "외교부 1·2차관 모두 '다자 통'…북미·북핵라인 물먹었다", 
                ` 유엔 총회 참석을 마친 문재인 대통령은 27일 귀국 중인 비행기 안에서 차관급 5명 교체를 결정했다. 외교부의 양 차관을 포함, 그 중 3명이 외교 라인이다.
 
                외교부 1차관에는 조현(61) 외교부 2차관이 임명됐다. 지난해 6월 2차관을 맡은 지 1년 3개월 만의 수평 이동이다. 조 차관은 직업 외교관(외시 13회)으로, 외교통상부 다자외교조정관과 주오스트리아 대사, 주인도 대사 등을 역임했다. 효율적이고 스마트한 업무 스타일로 정평이 나 있으며, 다자 외교 및 통상 외교 분야를 주로 맡아 왔다.`, "중앙일보", new Date(2018, 8, 20, 13, 24), "http://localhost"),
                new Article(2, "외교부 1·2차관 모두 '다자 통'…북미·북핵라인 물먹었다", 
                ` 유엔 총회 참석을 마친 문재인 대통령은 27일 귀국 중인 비행기 안에서 차관급 5명 교체를 결정했다. 외교부의 양 차관을 포함, 그 중 3명이 외교 라인이다.
 
                외교부 1차관에는 조현(61) 외교부 2차관이 임명됐다. 지난해 6월 2차관을 맡은 지 1년 3개월 만의 수평 이동이다. 조 차관은 직업 외교관(외시 13회)으로, 외교통상부 다자외교조정관과 주오스트리아 대사, 주인도 대사 등을 역임했다. 효율적이고 스마트한 업무 스타일로 정평이 나 있으며, 다자 외교 및 통상 외교 분야를 주로 맡아 왔다.`, "중앙일보", new Date(), "http://localhost")
            ],
        };
    }

    componentDidMount() {
        // Get article list
    }
    
    render() {
        return (<div id="content">
            <h3>최근 뉴스</h3>
            <ArticleListComponent
                articles={this.state.articles}/>
        </div>);
    }
}

