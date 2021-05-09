import axios from 'axios'

export const localStorage = {
    get(key) {
        return JSON.parse(localStorage.getItem(key))
    },
    set(key, value) {
        localStorage.setItem(key, JSON.stringify(value))
    }
}

// We use an IIFE to encapsulate our provider
export const http = (() => {
    function withDefaultHeaders (config, token) {
        return {
            ...config,
            headers: {
                ...config.headers || {},
                'Content-Type': 'application/json',
                'Authorization': `bearer ${token}`
            }
        }
    }

    function evaluateResponse (response) {
        if (response.status >= 200 && response.status < 300) {
            return response.toJSON()
        }

        return Promise.reject(response)
    }

    return {
        request(config = {}) {
            const token = this.context.localStorage.get('token')
            return fetch(config.url, withDefaultHeaders(config, token))
                .then(evaluateResponse)
        },
        get(url, config = {}) {
            const token = this.context.localStorage.get('token')
            config.url = url
            return fetch(url, withDefaultHeaders(config, token))
                .then(evaluateResponse)

        },
        post(url, data, config = {}) {
            const token = this.context.localStorage.get('token')
            config.url = url
            config.method = 'POST'
            config.body = JSON.stringify(data)

            return fetch(url, withDefaultHeaders(config, token))
                .then(evaluateResponse)
        },
        ...
    }
})()