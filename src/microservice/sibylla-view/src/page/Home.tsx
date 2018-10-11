import * as React from "react";

import { default as Header } from "../component/Header";
import { default as Content } from "../component/Content";

export default class HomePageComponent extends React.Component {

    render() {
        return (
            <div>
                <Header/>
                <Content/>
            </div>
        );
    }
}