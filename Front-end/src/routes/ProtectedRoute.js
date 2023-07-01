import React, {useContext, useEffect} from "react";
import {useNavigate} from "react-router-dom";
import UserContext from "../context/user-context";

const ProtectedRoute = ({roles, children}) => {
    const userCtx = useContext(UserContext);
    let navigate = useNavigate();

    useEffect(() => {
        if (!userCtx.token || (roles?.length && !roles.includes(userCtx.role))) {
            navigate('/login')
        }
    }, [navigate, roles, userCtx.role, userCtx.token]);

    return <>
        {children}
    </>;
};

export default ProtectedRoute;