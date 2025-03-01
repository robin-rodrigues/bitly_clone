import Footer from "./components/Footer";
import LandingPage from "./components/LandingPage";
import AboutPage from "./components/AboutPage";
import { Route, Routes } from "react-router-dom";
import Navbar from "./components/NavBar";
import RegisterPage from "./components/RegisterPage";
import { Toaster } from "react-hot-toast";
import LoginPage from "./components/LoginPage";
import DashboardLayout from "./components/dashboard/DashboardLayout";
import ShortenUrlPage from "./components/ShortenUrlPage";
import PrivateRoute from "./PrivateRoute";

const AppRouter = () => {
    return (
        <>
            <Navbar />
            <Toaster position="bottom-center" />
            <Routes>
                <Route path="/" element={<LandingPage />} />
                <Route path="/about" element={<AboutPage />} />
                <Route
                    path="/register"
                    element={
                        <PrivateRoute publicPage={true}>
                            <RegisterPage />
                        </PrivateRoute>
                    }
                />
                <Route
                    path="/login"
                    element={
                        <PrivateRoute publicPage={true}>
                            <LoginPage />
                        </PrivateRoute>
                    }
                />
                <Route
                    path="/dashboard"
                    element={
                        <PrivateRoute publicPage={false}>
                            <DashboardLayout />
                        </PrivateRoute>
                    }
                />
            </Routes>
            <Footer />
        </>
    );
};

export default AppRouter;

export const SubDomainRouter = () => {
    return (
        <Routes>
            <Route path="/:url" element={<ShortenUrlPage />} />
        </Routes>
    );
};
