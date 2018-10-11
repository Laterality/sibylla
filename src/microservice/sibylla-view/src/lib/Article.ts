export default class Article {
    constructor(
        public id: number,
        public title: string,
        public content: string,
        public sourceName: string,
        public writtenDate: Date,
        public url: string,
    ) {

    }
}