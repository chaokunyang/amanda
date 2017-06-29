var path = require('path');

module.exports = {
    entry: './src/main/js/admin/Index.js',
    devtool: 'sourcemaps',
    cache: true,
    debug: true,
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