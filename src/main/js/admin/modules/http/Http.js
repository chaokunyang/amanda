import Axios from 'axios'

const Http = Axios;
Http.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';

export default Http;