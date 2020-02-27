def split_to_dict(iterable, field):
    """
    Sort an iterable of items into multiple lists, based on the provided field.

    Example: split_to_dict(shirts, 'size')

    Arguments:
    iterable -- the iterable to be sorted
    field -- which field the iterable should be sorted on. Can be an index, key, or attribute

    Returns:
    defaultdict of lists, where the keys are the unique values of 'field'
    """
    from collections import defaultdict

    def guess_field_type(item, field):
        """
        Try each possible field type until we don't fail, then return that
        """
        if not (item and field):
            raise Exception("Could not guess field type!")

        # If it's an int, it's probably an index
        if isinstance(field, int):
            return "index"

        # Try it as a key first
        try:
            value = iterable[field]
            return "key"
        except KeyError:
            pass

        # Try it as an attribute
        try:
            value = getattr(iterable, field)
            return "attribute"
        except AttributeError:
            pass

        # Still don't know what it is
        raise Exception("Could not guess field type!")

    def get_field(item, field, field_type):
        """ Return the requested field """
        if field_type == "attribute":
            return getattr(item, field)
        else:
            return item[field]

    result = defaultdict(list)

    try:
        field_type = guess_field_type(next((item for item in iterable)), field)

        for item in iterable:
            result[get_field(item, field, field_type)].append(item)

    except Exception as error:
        raise error

    return result
