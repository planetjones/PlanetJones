var expect = require('chai').expect;
var functions = require('./index.js');

var request = {
    body: {
        name:null
    }

};
var response = {
    the_response: null,
    send:function(response) {
        this.the_response = response;
    }
};

describe('helloHTTP', function () {

    it('should say hello', function () {
        functions.helloHTTP(this.request, response);
        expect(response.the_response).to.equal('hello planetjones');
    });

});

describe('goodbyeHTTP', function () {

    it('should say goodbye', function () {
        functions.goodbyeHTTP(request, response);
        expect(response.the_response).to.equal('goodbye planetjones');
    });

});