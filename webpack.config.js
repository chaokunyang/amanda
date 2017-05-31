var path = require('path');

var node_dir = __dirname + '/node_modules';

module.exports = {
    entry: './src/main/js/admin/Index.js',
    devtool: 'sourcemaps',
    cache: true,
    debug: true,
    resolve: {
        alias: {
            'stompjs': node_dir + '/stompjs/lib/stomp.js',
        }
    },
    output: {
        path: __dirname,
        filename: './src/main/resources/static/built/bundle.js'
    },
    module: {
        loaders: [
            {
                test: /\.js[x]?$/,
                exclude: /node_modules/,
                loader: 'babel-loader?presets[]=es2015&presets[]=react'
            },
            { test: /\.css$/, loader: 'style-loader!css-loader' },
            {
                test: /\.(svg|png|jpg|jpeg|gif)$/i,
                loaders: ['file', 'image-webpack?bypassOnDebug&optimizationLevel=7&interlaced=false']
            }
        ]
    }
};