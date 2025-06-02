import cookie from 'react-cookies'

export default (current, action) => {
    if (action.type === 'update') {
        let cart = cookie.load('cart') || null;
        if (cart !== null) {
            let total = 0;
            for (let c of Object.values(cart))
                total += c.quantity;

            return total;
        }
    } else if (action.type === 'paid') {
        cookie.remove('cart');
        return 0;
    }
    return current;
}