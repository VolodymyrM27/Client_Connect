import { Navigate, useLocation } from 'react-router-dom';

//todo
function isAuthenticated() {
    // Возвращает объект пользователя с массивом ролей
    return {
        roles: ['admin', 'user']
    };
}

function RequireRoles({ children, allowedRoles }) {
    const location = useLocation();
    const user = isAuthenticated();

    return user && user.roles.some(role => allowedRoles.includes(role))
        ? children
        : <Navigate to="/login" state={{ from: location }} replace />;
}

function RequireAuth({ children }) {
    const location = useLocation();
    const user = isAuthenticated();

    return user
        ? children
        : <Navigate to="/login" state={{ from: location }} replace />;
}

export { RequireRoles, RequireAuth };
