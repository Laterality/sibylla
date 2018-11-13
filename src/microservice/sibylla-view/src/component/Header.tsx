import * as React from "react";
import { Component } from "react";
import { Cookies, withCookies } from "react-cookie";

import Dialog from "@material-ui/core/Dialog";
import DialogContent from "@material-ui/core/DialogContent";
import SwipeableViews from "react-swipeable-views";
import TextField from "@material-ui/core/TextField";
import Typography from "@material-ui/core/Typography";
import Api from "../lib/Api";
import { AxiosResponse } from "axios";

interface IHeaderComponentProps {
    signedIn: boolean;
    onSignedClick: (email: string, password: string) => void;
    onLogoutClick: () => void;
}

interface IHeaderComponentState {
    signDialogOpen: boolean;
    currentDialogIndex: number;
}

const style = {
    signUpText: {
        float: "right" as "right",
        fontWeight: 700,
        cursor: "pointer",
    },
    signInButton: {
        width: "100%",
        background: "#3e2723",
        color: "#ffffff",
    },
    logoutLink: {
        cursor: "pointer",
    },
    nonSignedInWrapper: {
        display: "flex",
        flexDirection: "row" as "row",
        alignItems: "center",
    }
};

export default class HeaderComponent extends Component<IHeaderComponentProps, IHeaderComponentState> {

    constructor(props: any) {
        super(props);

        this.state = {
            signDialogOpen: false,
            currentDialogIndex: 0,
        };

    }

    render() {
        const { props } = this;
        let profileComp;
        if (!props.signedIn) {
            profileComp = <button type="button" 
                className="btn btn-login my-3 mr-3"
                onClick={this.handleSignButtonClick}>로그인 / 회원가입</button>;
        } else {
            profileComp = <div style={style.nonSignedInWrapper}><a style={style.logoutLink} onClick={this.handleLogoutClick}>로그아웃</a><img src="./img/baseline_person_white_24dp.png" alt="user profile image" className="profile-img"/></div>;
        }

        return (<div id="header">
            {/* 로고 영역 */}
            <img src="./img/Logo_with_Typography.svg" alt="logo" className="logo" />
            {/* 검색창 영역 */}
            <div className="search-box-container">
                <img src="./img/baseline-search-24px.svg" alt="search icon"/>
                <input type="text" className="search-keyword-input" placeholder="기사 검색"/>
            </div>
            {/* 사용자 프로필 영역 */}
            {profileComp}

            <Dialog onClose={this.handleDialogClose} 
                open={this.state.signDialogOpen} >
                <DialogContent>
                    <SwipeableViews index={this.state.currentDialogIndex} onChangeIndex={this.handleChangeDialogIndex}>
                        <div>
                            {/* 로그인 영역 */}
                            <Typography variant="h5">로그인</Typography>
                            <TextField
                                id="input-sign-in-email"
                                label="이메일"
                                type="email"
                                margin="normal"
                                fullWidth
                                />
                            <TextField
                                id="input-sign-in-password"
                                label="비밀번호"
                                type="password"
                                margin="normal"
                                fullWidth
                                />
                            <button style={style.signInButton}
                                className="btn my-3"
                                onClick={this.handleSignInButtonClick}>로그인</button>
                            <a className="my-3"
                                style={style.signUpText}
                                onClick={() => this.handleChangeDialogIndex(1)}>회원가입</a>
                        </div>
                        <div>
                            {/* 회원가입 영역 */}
                            <Typography variant="h5">회원가입</Typography>
                            <TextField
                                id="input-sign-up-email"
                                label="이메일"
                                type="email"
                                margin="normal"
                                fullWidth
                                />
                            <TextField
                                id="input-sign-up-password"
                                label="비밀번호"
                                type="password"
                                margin="normal"
                                fullWidth
                                />
                            <button style={style.signInButton}
                                className="btn my-3">가입하기</button>
                            <a className="my-3"
                                style={style.signUpText}
                                onClick={() => this.handleChangeDialogIndex(0)}>이전</a>
                        </div>
                    </SwipeableViews>
                </DialogContent>
            </Dialog>
        </div>);
    }

    private handleDialogClose = () => {
        this.setState({signDialogOpen: false});
    }

    private handleSignButtonClick = () => {
        this.setState({signDialogOpen: true});
    }

    private handleChangeDialogIndex = (index: number) => {
        this.setState({currentDialogIndex: index});
    }

    private handleSignInButtonClick = () => {
        const signInInputEmail = document.getElementById("input-sign-in-email") as HTMLInputElement;
        const signInInputPassword = document.getElementById("input-sign-in-password") as HTMLInputElement;
        if (signInInputEmail === null || signInInputPassword === null) { return; }
        const email = signInInputEmail.value;
        const password = signInInputPassword.value;
        this.props.onSignedClick(email, password);
    }

    private handleLogoutClick = () => {
        this.props.onLogoutClick();
    }
}
