const webpack = require('webpack');

module.exports = {
    entry: './js/app.js',
    output: {
        path: './bin',
        filename: 'app.bundle.js'
    },
    module: {
        loaders: [{
            test: /\.js$/,
            exclude: /node_modules/,
            loader: 'babel-loader',
        }, {
            test: /\.css$/,
            loader: "style!css"
        }, {
            test: require.resolve('jquery'),
            loader: 'expose?jQuery!expose?$'
        }]
    }
};