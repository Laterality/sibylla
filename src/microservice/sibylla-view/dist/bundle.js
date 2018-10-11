/******/ (function(modules) { // webpackBootstrap
/******/ 	// The module cache
/******/ 	var installedModules = {};
/******/
/******/ 	// The require function
/******/ 	function __webpack_require__(moduleId) {
/******/
/******/ 		// Check if module is in cache
/******/ 		if(installedModules[moduleId]) {
/******/ 			return installedModules[moduleId].exports;
/******/ 		}
/******/ 		// Create a new module (and put it into the cache)
/******/ 		var module = installedModules[moduleId] = {
/******/ 			i: moduleId,
/******/ 			l: false,
/******/ 			exports: {}
/******/ 		};
/******/
/******/ 		// Execute the module function
/******/ 		modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);
/******/
/******/ 		// Flag the module as loaded
/******/ 		module.l = true;
/******/
/******/ 		// Return the exports of the module
/******/ 		return module.exports;
/******/ 	}
/******/
/******/
/******/ 	// expose the modules object (__webpack_modules__)
/******/ 	__webpack_require__.m = modules;
/******/
/******/ 	// expose the module cache
/******/ 	__webpack_require__.c = installedModules;
/******/
/******/ 	// define getter function for harmony exports
/******/ 	__webpack_require__.d = function(exports, name, getter) {
/******/ 		if(!__webpack_require__.o(exports, name)) {
/******/ 			Object.defineProperty(exports, name, {
/******/ 				configurable: false,
/******/ 				enumerable: true,
/******/ 				get: getter
/******/ 			});
/******/ 		}
/******/ 	};
/******/
/******/ 	// define __esModule on exports
/******/ 	__webpack_require__.r = function(exports) {
/******/ 		Object.defineProperty(exports, '__esModule', { value: true });
/******/ 	};
/******/
/******/ 	// getDefaultExport function for compatibility with non-harmony modules
/******/ 	__webpack_require__.n = function(module) {
/******/ 		var getter = module && module.__esModule ?
/******/ 			function getDefault() { return module['default']; } :
/******/ 			function getModuleExports() { return module; };
/******/ 		__webpack_require__.d(getter, 'a', getter);
/******/ 		return getter;
/******/ 	};
/******/
/******/ 	// Object.prototype.hasOwnProperty.call
/******/ 	__webpack_require__.o = function(object, property) { return Object.prototype.hasOwnProperty.call(object, property); };
/******/
/******/ 	// __webpack_public_path__
/******/ 	__webpack_require__.p = "";
/******/
/******/
/******/ 	// Load entry module and return exports
/******/ 	return __webpack_require__(__webpack_require__.s = "./src/index.tsx");
/******/ })
/************************************************************************/
/******/ ({

/***/ "./src/App.tsx":
/*!*********************!*\
  !*** ./src/App.tsx ***!
  \*********************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

var __extends = (this && this.__extends) || (function () {
    var extendStatics = function (d, b) {
        extendStatics = Object.setPrototypeOf ||
            ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
            function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
        return extendStatics(d, b);
    }
    return function (d, b) {
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
var __importStar = (this && this.__importStar) || function (mod) {
    if (mod && mod.__esModule) return mod;
    var result = {};
    if (mod != null) for (var k in mod) if (Object.hasOwnProperty.call(mod, k)) result[k] = mod[k];
    result["default"] = mod;
    return result;
};
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
var React = __importStar(__webpack_require__(/*! react */ "react"));
var Home_1 = __importDefault(__webpack_require__(/*! ./page/Home */ "./src/page/Home.tsx"));
var App = /** @class */ (function (_super) {
    __extends(App, _super);
    function App() {
        return _super !== null && _super.apply(this, arguments) || this;
    }
    App.prototype.render = function () {
        return (React.createElement(Home_1.default, null));
    };
    return App;
}(React.Component));
exports.default = App;


/***/ }),

/***/ "./src/component/ArticleList.tsx":
/*!***************************************!*\
  !*** ./src/component/ArticleList.tsx ***!
  \***************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

var __extends = (this && this.__extends) || (function () {
    var extendStatics = function (d, b) {
        extendStatics = Object.setPrototypeOf ||
            ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
            function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
        return extendStatics(d, b);
    }
    return function (d, b) {
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
var __importStar = (this && this.__importStar) || function (mod) {
    if (mod && mod.__esModule) return mod;
    var result = {};
    if (mod != null) for (var k in mod) if (Object.hasOwnProperty.call(mod, k)) result[k] = mod[k];
    result["default"] = mod;
    return result;
};
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
var React = __importStar(__webpack_require__(/*! react */ "react"));
var ArticleListItem_1 = __importDefault(__webpack_require__(/*! ./ArticleListItem */ "./src/component/ArticleListItem.tsx"));
var ArticleListComponent = /** @class */ (function (_super) {
    __extends(ArticleListComponent, _super);
    function ArticleListComponent() {
        return _super !== null && _super.apply(this, arguments) || this;
    }
    ArticleListComponent.prototype.render = function () {
        var props = this.props;
        var items = props.articles.map(function (v) {
            return React.createElement(ArticleListItem_1.default, { article: v, key: v.id });
        });
        return (React.createElement("div", { className: "article-list" }, items));
    };
    return ArticleListComponent;
}(React.Component));
exports.default = ArticleListComponent;


/***/ }),

/***/ "./src/component/ArticleListItem.tsx":
/*!*******************************************!*\
  !*** ./src/component/ArticleListItem.tsx ***!
  \*******************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

var __extends = (this && this.__extends) || (function () {
    var extendStatics = function (d, b) {
        extendStatics = Object.setPrototypeOf ||
            ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
            function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
        return extendStatics(d, b);
    }
    return function (d, b) {
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
var __importStar = (this && this.__importStar) || function (mod) {
    if (mod && mod.__esModule) return mod;
    var result = {};
    if (mod != null) for (var k in mod) if (Object.hasOwnProperty.call(mod, k)) result[k] = mod[k];
    result["default"] = mod;
    return result;
};
Object.defineProperty(exports, "__esModule", { value: true });
var React = __importStar(__webpack_require__(/*! react */ "react"));
var ArticleListItemComponent = /** @class */ (function (_super) {
    __extends(ArticleListItemComponent, _super);
    function ArticleListItemComponent() {
        return _super !== null && _super.apply(this, arguments) || this;
    }
    ArticleListItemComponent.prototype.render = function () {
        var props = this.props;
        var writtenDate = props.article.writtenDate;
        var now = new Date();
        var dspYear = now.getUTCFullYear() !== writtenDate.getUTCFullYear();
        var dspMonthDay = now.getUTCMonth() !== writtenDate.getUTCMonth() ||
            now.getUTCDate() !== writtenDate.getUTCDate();
        var writtenDateStr = "" + (dspYear ? writtenDate.getUTCFullYear() + ". " : "") + (dspMonthDay ? writtenDate.getMonth() + ". " + writtenDate.getUTCDate() + ". " : "") + writtenDate.getUTCHours() + ":" + writtenDate.getUTCMinutes();
        return (React.createElement("div", { className: "article-list-item" },
            React.createElement("h2", null, props.article.title),
            React.createElement("div", { className: "article-meta" },
                React.createElement("img", { src: "./img/joongang_logo_circle.png" }),
                React.createElement("h6", null, props.article.sourceName),
                React.createElement("h6", null,
                    "| ",
                    writtenDateStr),
                React.createElement("a", { href: props.article.url, className: "goto-source" }, "\uC6D0\uBB38 \uBCF4\uAE30")),
            React.createElement("p", null, props.article.content)));
    };
    return ArticleListItemComponent;
}(React.Component));
exports.default = ArticleListItemComponent;


/***/ }),

/***/ "./src/component/Content.tsx":
/*!***********************************!*\
  !*** ./src/component/Content.tsx ***!
  \***********************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

var __extends = (this && this.__extends) || (function () {
    var extendStatics = function (d, b) {
        extendStatics = Object.setPrototypeOf ||
            ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
            function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
        return extendStatics(d, b);
    }
    return function (d, b) {
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
var __importStar = (this && this.__importStar) || function (mod) {
    if (mod && mod.__esModule) return mod;
    var result = {};
    if (mod != null) for (var k in mod) if (Object.hasOwnProperty.call(mod, k)) result[k] = mod[k];
    result["default"] = mod;
    return result;
};
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
var React = __importStar(__webpack_require__(/*! react */ "react"));
var Article_1 = __importDefault(__webpack_require__(/*! ../lib/Article */ "./src/lib/Article.ts"));
var ArticleList_1 = __importDefault(__webpack_require__(/*! ./ArticleList */ "./src/component/ArticleList.tsx"));
var ContentComponent = /** @class */ (function (_super) {
    __extends(ContentComponent, _super);
    function ContentComponent(props) {
        var _this = _super.call(this, props) || this;
        _this.state = {
            articles: [
                new Article_1.default(1, "외교부 1·2차관 모두 '다자 통'…북미·북핵라인 물먹었다", " \uC720\uC5D4 \uCD1D\uD68C \uCC38\uC11D\uC744 \uB9C8\uCE5C \uBB38\uC7AC\uC778 \uB300\uD1B5\uB839\uC740 27\uC77C \uADC0\uAD6D \uC911\uC778 \uBE44\uD589\uAE30 \uC548\uC5D0\uC11C \uCC28\uAD00\uAE09 5\uBA85 \uAD50\uCCB4\uB97C \uACB0\uC815\uD588\uB2E4. \uC678\uAD50\uBD80\uC758 \uC591 \uCC28\uAD00\uC744 \uD3EC\uD568, \uADF8 \uC911 3\uBA85\uC774 \uC678\uAD50 \uB77C\uC778\uC774\uB2E4.\n \n                \uC678\uAD50\uBD80 1\uCC28\uAD00\uC5D0\uB294 \uC870\uD604(61) \uC678\uAD50\uBD80 2\uCC28\uAD00\uC774 \uC784\uBA85\uB410\uB2E4. \uC9C0\uB09C\uD574 6\uC6D4 2\uCC28\uAD00\uC744 \uB9E1\uC740 \uC9C0 1\uB144 3\uAC1C\uC6D4 \uB9CC\uC758 \uC218\uD3C9 \uC774\uB3D9\uC774\uB2E4. \uC870 \uCC28\uAD00\uC740 \uC9C1\uC5C5 \uC678\uAD50\uAD00(\uC678\uC2DC 13\uD68C)\uC73C\uB85C, \uC678\uAD50\uD1B5\uC0C1\uBD80 \uB2E4\uC790\uC678\uAD50\uC870\uC815\uAD00\uACFC \uC8FC\uC624\uC2A4\uD2B8\uB9AC\uC544 \uB300\uC0AC, \uC8FC\uC778\uB3C4 \uB300\uC0AC \uB4F1\uC744 \uC5ED\uC784\uD588\uB2E4. \uD6A8\uC728\uC801\uC774\uACE0 \uC2A4\uB9C8\uD2B8\uD55C \uC5C5\uBB34 \uC2A4\uD0C0\uC77C\uB85C \uC815\uD3C9\uC774 \uB098 \uC788\uC73C\uBA70, \uB2E4\uC790 \uC678\uAD50 \uBC0F \uD1B5\uC0C1 \uC678\uAD50 \uBD84\uC57C\uB97C \uC8FC\uB85C \uB9E1\uC544 \uC654\uB2E4.", "중앙일보", new Date(2018, 8, 20, 13, 24), "http://localhost"),
                new Article_1.default(2, "외교부 1·2차관 모두 '다자 통'…북미·북핵라인 물먹었다", " \uC720\uC5D4 \uCD1D\uD68C \uCC38\uC11D\uC744 \uB9C8\uCE5C \uBB38\uC7AC\uC778 \uB300\uD1B5\uB839\uC740 27\uC77C \uADC0\uAD6D \uC911\uC778 \uBE44\uD589\uAE30 \uC548\uC5D0\uC11C \uCC28\uAD00\uAE09 5\uBA85 \uAD50\uCCB4\uB97C \uACB0\uC815\uD588\uB2E4. \uC678\uAD50\uBD80\uC758 \uC591 \uCC28\uAD00\uC744 \uD3EC\uD568, \uADF8 \uC911 3\uBA85\uC774 \uC678\uAD50 \uB77C\uC778\uC774\uB2E4.\n \n                \uC678\uAD50\uBD80 1\uCC28\uAD00\uC5D0\uB294 \uC870\uD604(61) \uC678\uAD50\uBD80 2\uCC28\uAD00\uC774 \uC784\uBA85\uB410\uB2E4. \uC9C0\uB09C\uD574 6\uC6D4 2\uCC28\uAD00\uC744 \uB9E1\uC740 \uC9C0 1\uB144 3\uAC1C\uC6D4 \uB9CC\uC758 \uC218\uD3C9 \uC774\uB3D9\uC774\uB2E4. \uC870 \uCC28\uAD00\uC740 \uC9C1\uC5C5 \uC678\uAD50\uAD00(\uC678\uC2DC 13\uD68C)\uC73C\uB85C, \uC678\uAD50\uD1B5\uC0C1\uBD80 \uB2E4\uC790\uC678\uAD50\uC870\uC815\uAD00\uACFC \uC8FC\uC624\uC2A4\uD2B8\uB9AC\uC544 \uB300\uC0AC, \uC8FC\uC778\uB3C4 \uB300\uC0AC \uB4F1\uC744 \uC5ED\uC784\uD588\uB2E4. \uD6A8\uC728\uC801\uC774\uACE0 \uC2A4\uB9C8\uD2B8\uD55C \uC5C5\uBB34 \uC2A4\uD0C0\uC77C\uB85C \uC815\uD3C9\uC774 \uB098 \uC788\uC73C\uBA70, \uB2E4\uC790 \uC678\uAD50 \uBC0F \uD1B5\uC0C1 \uC678\uAD50 \uBD84\uC57C\uB97C \uC8FC\uB85C \uB9E1\uC544 \uC654\uB2E4.", "중앙일보", new Date(), "http://localhost")
            ],
        };
        return _this;
    }
    ContentComponent.prototype.componentDidMount = function () {
        // Get article list
    };
    ContentComponent.prototype.render = function () {
        return (React.createElement("div", { id: "content" },
            React.createElement("h3", null, "\uCD5C\uADFC \uB274\uC2A4"),
            React.createElement(ArticleList_1.default, { articles: this.state.articles })));
    };
    return ContentComponent;
}(React.Component));
exports.default = ContentComponent;


/***/ }),

/***/ "./src/component/Header.tsx":
/*!**********************************!*\
  !*** ./src/component/Header.tsx ***!
  \**********************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

var __extends = (this && this.__extends) || (function () {
    var extendStatics = function (d, b) {
        extendStatics = Object.setPrototypeOf ||
            ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
            function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
        return extendStatics(d, b);
    }
    return function (d, b) {
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
var __importStar = (this && this.__importStar) || function (mod) {
    if (mod && mod.__esModule) return mod;
    var result = {};
    if (mod != null) for (var k in mod) if (Object.hasOwnProperty.call(mod, k)) result[k] = mod[k];
    result["default"] = mod;
    return result;
};
Object.defineProperty(exports, "__esModule", { value: true });
var React = __importStar(__webpack_require__(/*! react */ "react"));
var react_1 = __webpack_require__(/*! react */ "react");
var HeaderComponent = /** @class */ (function (_super) {
    __extends(HeaderComponent, _super);
    function HeaderComponent() {
        return _super !== null && _super.apply(this, arguments) || this;
    }
    HeaderComponent.prototype.render = function () {
        return (React.createElement("div", { id: "header" },
            React.createElement("img", { src: "./img/Logo_with_Typography.svg", alt: "logo", className: "logo" }),
            React.createElement("div", { className: "search-box-container" },
                React.createElement("img", { src: "./img/baseline-search-24px.svg", alt: "search icon" }),
                React.createElement("input", { type: "text", className: "search-keyword-input", placeholder: "\uAE30\uC0AC \uAC80\uC0C9" })),
            React.createElement("img", { src: "./img/baseline_person_white_24dp.png", alt: "user profile image", className: "profile-img" })));
    };
    return HeaderComponent;
}(react_1.Component));
exports.default = HeaderComponent;


/***/ }),

/***/ "./src/index.tsx":
/*!***********************!*\
  !*** ./src/index.tsx ***!
  \***********************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
var react_1 = __importDefault(__webpack_require__(/*! react */ "react"));
var react_dom_1 = __importDefault(__webpack_require__(/*! react-dom */ "react-dom"));
var App_1 = __importDefault(__webpack_require__(/*! ./App */ "./src/App.tsx"));
react_dom_1.default.render(react_1.default.createElement(App_1.default, null), document.getElementById("root"));


/***/ }),

/***/ "./src/lib/Article.ts":
/*!****************************!*\
  !*** ./src/lib/Article.ts ***!
  \****************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

Object.defineProperty(exports, "__esModule", { value: true });
var Article = /** @class */ (function () {
    function Article(id, title, content, sourceName, writtenDate, url) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.sourceName = sourceName;
        this.writtenDate = writtenDate;
        this.url = url;
    }
    return Article;
}());
exports.default = Article;


/***/ }),

/***/ "./src/page/Home.tsx":
/*!***************************!*\
  !*** ./src/page/Home.tsx ***!
  \***************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

var __extends = (this && this.__extends) || (function () {
    var extendStatics = function (d, b) {
        extendStatics = Object.setPrototypeOf ||
            ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
            function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
        return extendStatics(d, b);
    }
    return function (d, b) {
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
var __importStar = (this && this.__importStar) || function (mod) {
    if (mod && mod.__esModule) return mod;
    var result = {};
    if (mod != null) for (var k in mod) if (Object.hasOwnProperty.call(mod, k)) result[k] = mod[k];
    result["default"] = mod;
    return result;
};
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
var React = __importStar(__webpack_require__(/*! react */ "react"));
var Header_1 = __importDefault(__webpack_require__(/*! ../component/Header */ "./src/component/Header.tsx"));
var Content_1 = __importDefault(__webpack_require__(/*! ../component/Content */ "./src/component/Content.tsx"));
var HomePageComponent = /** @class */ (function (_super) {
    __extends(HomePageComponent, _super);
    function HomePageComponent() {
        return _super !== null && _super.apply(this, arguments) || this;
    }
    HomePageComponent.prototype.render = function () {
        return (React.createElement("div", null,
            React.createElement(Header_1.default, null),
            React.createElement(Content_1.default, null)));
    };
    return HomePageComponent;
}(React.Component));
exports.default = HomePageComponent;


/***/ }),

/***/ "react":
/*!************************!*\
  !*** external "React" ***!
  \************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = React;

/***/ }),

/***/ "react-dom":
/*!***************************!*\
  !*** external "ReactDOM" ***!
  \***************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = ReactDOM;

/***/ })

/******/ });
//# sourceMappingURL=bundle.js.map