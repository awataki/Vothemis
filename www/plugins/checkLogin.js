export default ({ app, store }) => {
  app.router.afterEach(() => {
    if (store.state.Token.aToken === '') {
      app.router.push('/login')
    }
  })
}
