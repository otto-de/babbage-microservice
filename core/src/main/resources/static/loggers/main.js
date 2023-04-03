window.logger = function () {

    const _getBaseUrl = () => {
        let container = document.querySelector(".container[data-base-path]");
        if (container) {
            return container.getAttribute("data-base-path");
        } else {
            return "";
        }
    };

    const _getManagementBasePath = () => {
        return document.querySelector(".container[data-management-base-path]").getAttribute("data-management-base-path")
    }

    const _loggerChanged = (btn) => {
        const loggerName = btn.dataset['name'];
        const logLevelState = btn.textContent;

        const headers = new Headers();
        headers.set("Content-Type", "application/json");
        const fetchData = {
            headers: headers,
            method: 'POST',
            body: JSON.stringify({
                configuredLevel: logLevelState
            })
        }

        fetch(_getBaseUrl() + _getManagementBasePath() + `/loggers/${loggerName}`, fetchData)
            .then(function (response) {
                console.log(response);
                window.location.reload(false);
            })
            .catch(function (error) {
                console.log(error);
            });
    }


    const _filterChanged = (input) => {
        const text = input.value;
        document.querySelectorAll('.logger-row')
            .forEach(entry => {
                if ("name" in entry.dataset) {
                    let found = text.trim() === '' || entry.dataset['logger-name'].toLowerCase().indexOf(text.toLowerCase()) !== -1;
                    entry.style.display = found ? 'flex' : 'none';
                }
            });
    }

    return {
        loggerChanged: _loggerChanged,
        filterChanged: _filterChanged
    }
}();
