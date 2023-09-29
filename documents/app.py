FILE_NAME = 'Map_v2'

with open(f'{FILE_NAME}.csv', 'r') as f:
    data = f.read()

    output = ''
    for idx, val in enumerate(data.split('\n')):
        output += f'{{ {val} }},\n'

    with open(f'{FILE_NAME}.txt', 'w') as f:
        f.write(output)
