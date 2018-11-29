const url = process.env.REACT_APP_API_URL;

export function fetchJson(path) {
  return (fetch(url + path, {
    method: 'GET'
  }).then(results => {
    return results.json();
  }));
}

export function postJson(path, body) {
  return (fetch(url + path, {
    method: "POST",
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(body)
  }));
}