export default class ArticleImage {
    constructor(
        public id: number,
        public articleId: number,
        public src: string,
        public regDate: Date,
        public modDate: Date,
    ) {

    }
}