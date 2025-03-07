let screen = document.getElementById('screen');
let currentInput = '';

function appendNumber(number) {
    currentInput += number;
    updateScreen();
}

function appendOperation(operation) {
    if (currentInput !== '') {
        currentInput += ' ' + operation + ' ';
        updateScreen();
    }
}

function appendDot() {
    if (!currentInput.includes('.')) {
        currentInput += '.';
        updateScreen();
    }
}

function clearScreen() {
    currentInput = '';
    updateScreen();
}

function backspace() {
    currentInput = currentInput.slice(0, -1);
    updateScreen();
}

function updateScreen() {
    screen.value = currentInput;
}

function calculate() {
    try {
        let result = eval(currentInput);
        if (result === Infinity || isNaN(result)) {
            throw new Error('Невірний вираз');
        }
        currentInput = result.toString();
        updateScreen();
    } catch (error) {
        currentInput = 'Помилка';
        updateScreen();
    }
}
