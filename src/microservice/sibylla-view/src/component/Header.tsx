import * as React from "react";
import { Component } from "react";

export default class HeaderComponent extends Component {


    render() {
        return (<div id="header">
            {/* 로고 영역 */}
            <img src="./img/Logo_with_Typography.svg" alt="logo" className="logo" />
            {/* 검색창 영역 */}
            <div className="search-box-container">
                <img src="./img/baseline-search-24px.svg" alt="search icon"/>
                <input type="text" className="search-keyword-input" placeholder="기사 검색"/>
            </div>
            {/* 사용자 프로필 영역 */}
            <img src="./img/baseline_person_white_24dp.png" alt="user profile image" className="profile-img"/>
        </div>);
    }
}