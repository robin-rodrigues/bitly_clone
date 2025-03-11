import { subDomainList } from "./constant";
import { SubDomainRouter } from "../AppRouter";

export const getApps = () => {
    const subdomain = getSubDomain(window.location.hostname);
    const mainApp = subDomainList.find((app) => app.main);

    // Check if the path matches a shortened URL (e.g., '/eDaqQ0Vd')
    const isShortenedUrl = /^\/[a-zA-Z0-9]+$/.test(window.location.pathname);

    if (isShortenedUrl) return SubDomainRouter;

    if (subdomain === "") return mainApp.app;

    const apps = subDomainList.find((app) => subdomain === app.subdomain);
    return apps ? apps.app : mainApp.app;
};

export const getSubDomain = (location) => {
    const locationParts = location.split(".");

    const isLocalhost = locationParts.slice(-1)[0] === "localhost";

    const sliceTill = isLocalhost ? -1 : -2;

    return locationParts.slice(0, sliceTill).join("");
};
