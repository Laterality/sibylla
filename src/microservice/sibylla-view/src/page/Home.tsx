import * as React from "react";

import { default as Header } from "../component/Header";
import { default as Content } from "../component/Content";

interface IHomePageComponentProps {
    location: Location;
}

export default class HomePageComponent extends React.Component<IHomePageComponentProps> {

    render() {
        const style = {
            background: "#fafafa",
        };

        return (
            <div style={style}>
                <Header/>
                <Content/>
            </div>
        );
    }
}