import { useState } from 'react';
export const deepEquals = (a1, a2) => {
    let result;
    if (typeof a1 === 'object' && Array.isArray(a1) && typeof a2 === 'object' && Array.isArray(a2)) {
        result = a1.length === a2.length && a1.every((value, index) => deepEquals(value, a2[index]));
    } else if (a1 && a2 && typeof a1 === 'object' && typeof a2 === 'object') {
        result = Object.keys(a1).length === Object.keys(a2).length && Object.keys(a1).every(value => deepEquals(a1[value], a2[value]));
    } else {
        result = a1 === a2;
    }
    return result;
};
export const isDefined = value => {
    if (value === null || value === undefined) {
        return false;
    }
    if (typeof value === 'object') {
        return Object.values(value).some(prop => isDefined(prop));
    }
    return true;
};
export const nonDefined = value => !isDefined(value);
const useValidatedState = (state, validations) => {
    // eslint-disable-next-line no-underscore-dangle
    const _validate = (request, _validations = {}, parent = request, root = request) => {
        const errors = Object.keys(_validations).reduce((acc, k) => {
            if (_validations[k] && _validations[k].constructor && _validations[k].call && _validations[k].apply) {
                return {
                    ...acc,
                    ...{
                        [k]: _validations[k] && _validations[k](request, parent, root)
                    }
                };
            }
            if (typeof _validations[k] === 'object' && Array.isArray(_validations[k])) {
                var _request$k;
                return {
                    ...acc,
                    ...{
                        [k]: (_request$k = request[k]) === null || _request$k === void 0 ? void 0 : _request$k.map(it => _validate(it, _validations[k][0], request, root).errors)
                    }
                };
            }
            if (typeof _validations[k] === 'object') {
                return {
                    ...acc,
                    ...{
                        [k]: _validate(request === null || request === void 0 ? void 0 : request[k], _validations[k], request, root).errors
                    }
                };
            }
            return {
                ...acc,
                ...{
                    [k]: undefined
                }
            };
        }, {});
        return {
            errors,
            isFailure: () => isDefined(errors),
            isSuccess: () => nonDefined(errors),
            throwErrorIfFail: message => {
                if (isDefined(errors)) {
                    throw new Error(message);
                }
            }
        };
    };
    const [_state, _setState] = useState({
        state,
        errors: _validate(state, validations).errors,
        validations
    });
    const setState = newState => {
        let validation;
        _setState(prevState => {
            const stateUpdated = typeof newState === 'function' ? newState(prevState.state) : newState;
            validation = _validate(stateUpdated, prevState.validations);
            return {
                state: stateUpdated,
                errors: validation.errors,
                validations: prevState === null || prevState === void 0 ? void 0 : prevState.validations
            };
        });
        return validation;
    };
    const validate = (newValidations = _state.validations) => {
        const validation = _validate(_state.state, newValidations);
        _setState({
            ..._state,
            ...{
                errors: validation.errors,
                validations: newValidations
            }
        });
        return validation;
    };
    return {
        state: _state.state,
        errors: _state.errors,
        setState,
        validate,
        invalidate() {
            _setState({
                state,
                errors: _validate(state, validations).errors,
                validations
            });
        }
    };
};
export default useValidatedState;