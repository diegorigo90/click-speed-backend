// eslint-disable-next-line no-restricted-globals
self.onmessage = event => {
    console.log(event)
    console.log(event.data)
    // Do some computation with the data from the parent
    fetch(event.data.origin + "/clicks", {
        headers: new Headers({ 'content-type': 'application/json' }),
        method: "POST",
        body: JSON.stringify(event.data),
        mode: "cors"
    })

};