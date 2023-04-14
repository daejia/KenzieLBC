const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const CopyPlugin = require("copy-webpack-plugin");
const { CleanWebpackPlugin } = require('clean-webpack-plugin');

module.exports = {
  optimization: {
    usedExports: true
  },
  entry: {
    examplePage: path.resolve(__dirname, 'src', 'pages', 'examplePage.js'),
    productSearch: path.resolve(__dirname, 'src', 'pages', 'productSearchPage.js'),
    checkoutPage: path.resolve(__dirname, 'src', 'pages', 'checkoutPage.js'),
    UpdateCart: path.resolve(__dirname, 'src', 'pages', 'updateCartPage.js'),
  },
  output: {
    path: path.resolve(__dirname, 'dist'),
    filename: '[name].js',
  },
  devServer: {
    https: false,
    port: 8080,
    open: true,
    openPage: 'http://localhost:8080',
    // disableHostChecks, otherwise we get an error about headers and the page won't render
    disableHostCheck: true,
    contentBase: 'packaging_additional_published_artifacts',
    // overlay shows a full-screen overlay in the browser when there are compiler errors or warnings
    overlay: true
  },
  plugins: [
    new HtmlWebpackPlugin({
      template: './src/index.html',
      filename: 'index.html',
      inject: false
    }),
    new HtmlWebpackPlugin({
      template: './src/productSearch.html',
      filename: 'productSearch.html',
      inject: false
    }),
    new HtmlWebpackPlugin({
      template: './src/checkoutPage.html',
      filename: 'checkoutPage.html',
      inject: false
    }),
    new HtmlWebpackPlugin({
      template: './src/shoppingList.html',
      filename: 'shoppingList.html',
      inject: false
    }),
    new CopyPlugin({
      patterns: [
        {
          from: path.resolve('src/css'),
          to: path.resolve("dist/css")
        }
      ]
    }),
    new CleanWebpackPlugin()
  ]
}
