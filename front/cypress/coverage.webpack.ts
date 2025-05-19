import * as path from 'path';

export default {
  module: {
    rules: [
      {
        test: /\.ts$/,
        use: [
          {
            loader: 'ts-loader',
            options: {
              transpileOnly: true
            }
          },
          {
            loader: '@jsdevtools/coverage-istanbul-loader',
            options: { esModules: true }
          }
        ],
        enforce: 'post',
        include: path.join(__dirname, '..', 'src'),
        exclude: [
          /\.(e2e|spec)\.ts$/,
          /node_modules/,
          /(ngfactory|ngstyle)\.js/,
        ],
      }
    ]
  }
};
