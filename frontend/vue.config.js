// const { defineConfig } = require('@vue/cli-service')
// module.exports = defineConfig({
//   transpileDependencies: true
// })

module.exports = {
  transpileDependencies: true,
  publicPath: '/',
  css: {
    loaderOptions: {
      sass: {
        additionalData: `
        @import "@/scss/main.scss";
        `,
      },
    },
  },
}
