import * as React from "react";

interface IArticleMetaComponentProps {
    sourceName: string;
    writtenDate: Date;
    url: string;
}

export default class ArticleMetaComponent extends React.Component<IArticleMetaComponentProps> {


    render() {
        const { props } = this;
        const { writtenDate } = this.props;
        const now = new Date();
        const dspYear = now.getUTCFullYear() !== writtenDate.getUTCFullYear();
        const dspMonthDay = now.getUTCMonth() !== writtenDate.getUTCMonth() || 
            now.getUTCDate() !== writtenDate.getUTCDate();
        const writtenDateStr = `${dspYear? writtenDate.getUTCFullYear() + ". " : ""}${dspMonthDay? writtenDate.getMonth() + ". " + writtenDate.getUTCDate() + ". " : ""}${writtenDate.getUTCHours()}:${writtenDate.getUTCMinutes()}`;
        return (<div>
            <div className="article-meta">
                <img src="./img/joongang_logo_circle.png"/>
                <h6>{props.sourceName}</h6>
                <h6>| {writtenDateStr}</h6>
                <a href={props.url} className="goto-source py-1">원문 보기</a>
            </div>
        </div>);
    }
}